var User = require("../model/model.user.js")


exports.login = function(req,res){
    var user = new User();
    user.email = req.body.email;
    user.pass = req.body.pass;
    

    User.find({email:user.email, pass:user.pass})
    .then(function(user){
        console.log(user)
        res.send({message: "Login successful"})


    })
    .catch(function(err){
        console.log(err)
    })
}

exports.signup = function(req,res){
    var user = new User();
    user.name = req.body.name;
    user.email = req.body.email;
    user.pass = req.body.pass;


    user.save()
    .then(function(user){
        console.log(user)
        res.send({message: "Signup successful"})

    })
    .catch(function(err){
        console.log(err)
    })
}