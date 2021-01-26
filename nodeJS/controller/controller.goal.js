var Goal = require("../model/model.goal.js")


exports.insertGoalData = function(req,res){
    var goal = new Goal();
    
    goal.title = req.body.title;
    goal.amount = req.body.amount;
    goal.currency = req.body.currency;
    goal.date = req.body.date;

    goal.save()
    .then(function(data){
        console.log("Goal inserted")
        console.log(data)
        res.send({message: "Goal inserted"})
    })
    .catch(function(err){
        console.log("Goal insertion failed")
        res.send({message: "Goal insertion failed"})
    })
}

exports.updateGoalData = function(req,res){
    var goal = new Goal();
    
    goal.title = req.body.title;
    goal.amount = req.body.amount;
    goal.currency = req.body.currency;
    goal.date = req.body.date;

    goal.save()
    .then(function(data){
        console.log("Goal inserted")
        console.log(data)
        res.send({message: "Goal inserted"})
    })
    .catch(function(err){
        console.log("Goal insertion failed")
        res.send({message: "Goal insertion failed"})
    })
}



exports.getGoalData = function(req,res){


    Goal.find()
    .then(function(data){
        console.log("Goals found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
}


