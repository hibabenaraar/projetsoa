const express = require('express');
const cors = require('cors');
require('dotenv').config();

const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

const app = express();

app.use(cors());
app.use(express.json());

// -------------------- PAYMENT API --------------------

app.post('/api/payments/charge', async (req, res) => {
    try {
        const { orderId, amount, currency } = req.body;

        if (!orderId || !amount || !currency) {
            return res.status(400).json({
                status: 'Invalid Request',
                error: 'Les paramètres orderId, amount et currency sont obligatoires'
            });
        }

        if (typeof amount !== 'number' || amount <= 0) {
            return res.status(400).json({
                status: 'Invalid Request',
                error: 'Le montant doit être un nombre positif'
            });
        }

        const charge = await stripe.charges.create({
            amount: Math.round(amount),
            currency: currency.toLowerCase(),
            source: 'tok_visa',
            description: `Paiement pour la commande ${orderId}`,
            metadata: {
                orderId: orderId
            }
        });

        res.status(200).json({
            status: 'Success',
            transactionId: charge.id,
            amount: charge.amount,
            currency: charge.currency,
            orderId: orderId
        });

    } catch (error) {
        console.error('Erreur de paiement:', error.message);

        res.status(402).json({
            status: 'Payment Failed',
            error: error.message || 'Erreur lors du traitement du paiement'
        });
    }
});

// -------------------- HEALTH CHECK --------------------

app.get('/api/payments/health', (req, res) => {
    res.status(200).json({
        status: 'Payment Service is running',
        port: 3001
    });
});

// -------------------- SERVER START --------------------

const PORT = process.env.PORT || 3001;

app.listen(PORT, () => {
    console.log(`Payment Service actif sur le port ${PORT}`);
});

// -------------------- EUREKA CLIENT --------------------

const { Eureka } = require('eureka-js-client');

const client = new Eureka({
    instance: {
        instanceId: 'payment-service:3001',
        app: 'PAYMENT-SERVICE',

        hostName: 'localhost',
        ipAddr: '127.0.0.1',

        statusPageUrl: 'http://localhost:3001/api/payments/health',
        healthCheckUrl: 'http://localhost:3001/api/payments/health',
        homePageUrl: 'http://localhost:3001',

        port: {
            '$': 3001,
            '@enabled': true,
        },

        vipAddress: 'payment-service',

        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        }
    },

    eureka: {
        host: 'localhost',
        port: 8761,
        servicePath: '/eureka/apps/',
    },
});

client.start((error) => {
    if (error) {
        console.log('Eureka registration failed:', error);
    } else {
        console.log('Payment service registered in Eureka');
    }
});