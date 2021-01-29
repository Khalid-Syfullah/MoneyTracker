const express = require('express');
const router = express.Router();

module.exports = (app) => {
    var goal = require("../controller/controller.goal.js")

    app.post('/insertGoalData', goal.insertGoalData)
    app.post('/updateGoalData', goal.updateGoalData)
    app.post('/getGoalData', goal.getGoalData)

}