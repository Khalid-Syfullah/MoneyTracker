const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

module.exports = (app) => {
    var category = require("../controller/controller.category.js")

    app.post('/insertCategoryData', category.insertCategoryData)
    app.post('/getCategoryData', category.getCategoryData)
}