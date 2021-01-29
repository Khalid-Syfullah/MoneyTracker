const express = require('express');
const router = express.Router();

module.exports = (app) => {
    var user = require("../controller/controller.user.js")

    router.post('/login', user.login)
    router.post('/signup', user.signup)

}