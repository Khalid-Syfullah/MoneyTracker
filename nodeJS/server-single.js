var express = require("express")
var app = express()
var mongoose = require("mongoose")
var bodyParser = require("body-parser")


var userSchema = new mongoose.Schema({
    name: {type: String, required: true},
    email: {type: String, required: true},
    pass: {type: String, required: true}
})

var overviewSchema = new mongoose.Schema({
    spent: {type: Number, required: true},
    remaining: {type: Number, required: true},
    limit: {type: Number, required: true}
})

var transactionSchema = new mongoose.Schema({
    email: {type: String, required: true},
    title: {type: String, required: true},
    category: {type: String, required: true},
    wallet: {type: String, required: true},
    date: {type: String, required: true},
    amount: {type: Number, required: true},
    transaction: {type: String, required: true}

})


var walletSchema = new mongoose.Schema({
    title: {type: String},
    type: {type: String},
    currency: {type: String},
    balance: {type: String},
    email:{type:String}
})


var goalSchema = new mongoose.Schema({
    title: {type: String},
    amount: {type: String},
    currency: {type: String},
    date: {type: String}
})

var categorySchema = new mongoose.Schema({
    title: {type: String}
})

var User = mongoose.model("user",userSchema)
var Overview = mongoose.model("overview",overviewSchema)
var Transaction = mongoose.model("transaction",transactionSchema)
var Wallet = mongoose.model("wallet",walletSchema)
var Goal = mongoose.model("goal",goalSchema)
var Category = mongoose.model("category",categorySchema)

var url = "mongodb://localhost/moneytracker"

mongoose.connect(url, function(err){
    if(err){
		console.log("Unable to connect to database: " + err)
    }
    else{
		console.log("Connected to database!")
    }
})

app.use(bodyParser.urlencoded({extended: true}))
app.use(bodyParser.json())
app.listen(8800, function(){
    console.log("Listening to port 8800")
})



app.post('/api/login', function(req,res){
    var user = new User();
    user.email = req.body.email;
    user.pass = req.body.pass;
    


    User.find({email:user.email, pass:user.pass},(err,results)=>{

        if(err){
            console.log(err)
            return
        }
        else{
            if(results.length==0){
                res.send({serverMsg: "not found"})
                return
            }
            else{
                var response={'name' : results[0].name,'email':results[0].email,'pass':results[0].pass,'serverMsg':'successful'}
                res.send(response)
                
                console.log(response)
                return
            }
        }

    })
    // .then(function(user){
    //     console.log(user)
    //     res.send({serverMsg: "successful"})


    // })
    // .catch(function(err){
    //     console.log(err)
    // })
})


app.post('/api/signup', function(req,res){
    var user = new User();
    user.name = req.body.name;
    user.email = req.body.email;
    user.pass = req.body.pass;

    User.find({email:user.email},(err,results)=>{
        if(err){
            console.log(err)
        }
        else{
            if(results.length>0){
                res.send({ serverMsg: "exists" });
                return
            }
            else{
                user.save().then(data=>{
                    console.log(data)
                    res.send({serverMsg: "Registration successful"})
                }).catch((err)=>{
                    console.log(err)
                    res.send({serverMsg: "Failed"})
                })

            }
        }
    })


    // user.save()
    // .then(function(user){
    //     console.log(user)
    //     res.send({message: "Signup successful"})

    // })
    // .catch(function(err){
    //     console.log(err)
    // })
})

app.post('/api/insertTransactionData', function(req,res){
    var transaction = new Transaction();
    transaction.email = req.body.email;
    transaction.title = req.body.title;
    transaction.amount = req.body.amount;
    transaction.transaction = req.body.transaction;
    transaction.category = req.body.category;
    transaction.wallet = req.body.wallet;
    transaction.date = req.body.date;

    transaction.save()
    .then(function(data){
        console.log("Transaction inserted")
        console.log(data)
        res.send({message: "Transaction successful"})
    })
    .catch(function(err){
        console.log("Transaction failed")
        res.send({message: "Transaction failed"})
    })
})


app.post('/api/insertWalletData', function(req,res){
    var wallet = new Wallet();
    
    wallet.title = req.body.title;
    wallet.type = req.body.type;
    wallet.currency = req.body.currency;
    wallet.email=req.body.email
    wallet.balance = "0";

    Wallet.find({email:wallet.email,title:wallet.title},(err,results)=>{
        if(err){
            console.log(err)
            res.send({serverMsg:'error occured'})
            return
        }
        else{
            if(results.length>0){
                res.send({serverMsg: 'wallet exists'})
                return
            }
            else{
                wallet.save()
                .then(function(data){
                    console.log("Wallet inserted")
                    console.log(data)
                    res.send({serverMsg: "Wallet inserted"})
                })
                .catch(function(err){
                    console.log("Wallet insertion failed")
                    res.send({serverMsg: "Wallet insertion failed"})
                })
            }
        }
    })

  
})

app.post('/api/insertGoalData', function(req,res){
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
})




app.post('/api/insertCategoryData', function(req,res){
    var category = new Category();
    
    category.title = req.body.title;

    category.save()
    .then(function(data){
        console.log("Category inserted")
        console.log(data)
        res.send({message: "Category inserted"})
    })
    .catch(function(err){
        console.log("Category insertion failed")
        res.send({message: "Category insertion failed"})
    })
})




app.post('/api/getOverviewData', function(req,res){

    var overview = new Overview();
    overview.spent = 0;
    overview.remaining = 0;
    overview.limit = 0;

    Transaction.find({email:req.body.email})
    .then(function(results){

        if(results.length>0){
           

            console.log("Overview found")
            console.log(overview)

            for(result of results){

                overview.spent += result.amount
                overview.remaining += result.amount
                overview.limit += result.amount

                console.log("Transactions :")
                console.log(result)
            }



        }

        res.send(overview)

    })
    .catch(function(err){
        res.send({serverMsg: "Response error"})

    })
    
    
})


app.post('/api/getTransactionData', function(req,res){

    Transaction.find({email: req.body.email})
    .then(function(data){
        console.log("Transaction found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})

app.post('/api/getWalletData', function(req,res){


    Wallet.find({email: req.body.email})
    .then(function(data){
        console.log("Wallets found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})



app.post('/api/getGoalData', function(req,res){


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
})



app.post('/api/getCategoryData', function(req,res){


    Category.find()
    .then(function(data){
        console.log("Category found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})

app.post('/api/updateGoalData', function(req,res){
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
})

app.post('/api/updateWalletData', function(req,res){
    var wallet = new Wallet();

    var oldTitle=req.body.oldTitle;
    
    wallet.title = req.body.title;
    wallet.email = req.body.email;
    wallet.currency = req.body.currency;
    wallet.type = req.body.type;

    
    Wallet.find({title:wallet.title,email:wallet.email},(err,result)=>{
        if(err){
            console.log(err)

        }
        else{
            if(result.length>0){
                res.send({serverMsg:'wallet name exists'})
            }
            else{
                 var updateQuery={$set: {title: req.body.title,currency:req.body.currency,type:req.body.type}}
    Wallet.updateOne({title:oldTitle,email:wallet.email},updateQuery,(err,data)=>{
        if(err)
        console.log(err)
        else {res.send({serverMsg:'updated'})
    console.log(data)
    }

    })
            }
        }
    })
   
   
})

app.post('/api/deleteWalletData', function(req,res){
    var wallet = new Wallet();
    
    // wallet.title = req.body.title;
    // wallet.type = req.body.type;
    // wallet.currency = req.body.currency;
    // wallet.email=req.body.email

    Wallet.remove({email:req.body.email,title:req.body.title},(err,data)=>{
        if(err){
            res.send({serverMsg:'failed'})
        }
        else{
            res.send({serverMsg:'deleted'})
        }
    })

    

  
})

app.post('/api/user', function(req,res){
    var user = new User();
    user.email = req.body.name;
    user.pass = req.body.pass;


    user.save()
    .then(function(data){
        console.log("User created")
        res.send({message: "User created"})
    })
    .catch(function(err){
        console.log("User cannot be created")
        res.send({message: "User cannot be created"})
    })
})


