const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

module.exports = (app) => {
    var transaction = require("../controller/controller.transaction.js")

    app.post('/insertTransactionData', transaction.insertTransactionData)
}