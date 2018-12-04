// DEPENDENCIES
var express = require('express'),
    app = express(),
    bodyParser = require('body-parser'); // Middleware to read POST data

var mongodb = require('mongodb');
var MongoClient = mongodb.MongoClient;

// SETUP
// Set up body-parser.
/** bodyParser.urlencoded(options)
 * Parses the text as URL encoded data (which is how browsers tend to send form data from regular forms set to POST)
 * and exposes the resulting object (containing the keys and values) on req.body
 */
app.use(bodyParser.urlencoded({
    extended: true
}));
/**bodyParser.json(options)
 * Parses the text as JSON and exposes the resulting object on req.body.
 */
app.use(bodyParser.json());

// VARs
var mongoUrl = "mongodb://localhost:27017/";
var dbName = "AndroidEbayDB";

// Connexion
MongoClient.connect(mongoUrl, function (error, db) {
    if (error) return funcCallback(error);

    db = db.db(dbName);
    db.createCollection("users", function (err, res) {
        if (err) throw err;
    });
    db.createCollection("auctions", function (err, res) {
        if (err) throw err;
    });
    db.createCollection("bids", function (err, res) {
        if (err) throw err;
    });
});


// Routes
app.get('/', function (req, res) {
    res.setHeader('Content-Type', 'text/html');
    res.send('<h1>Bienvenue sur mon server API REST eBay</h1>');
})


// USERS

// getAllUsers
app.get('/users', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("users").find({}).toArray(function (error, result) {
            if (error) throw error;
            res.json(result);
        });
    });
})

// getUserByUserName
app.get('/users/getUserByUserName/:username', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("users").findOne({ username: req.params.username }, function (error, result) {
            if (error) throw error;
            res.json(result);
        });
    });
})

// addNewUser
/*{ 
	"username" : "entrax",
	"pwd" : "Alakazam36/",
	"lastname" : "Fines",
	"firstname" : "Guillaume",
	"mail" : "entrax.643@gmail.com",
	"adress" : "24 Rue du Sergent Vigné 31500 Toulouse"
    "photoURL" : ""
}*/
app.post('/user', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("users").insertOne(req.body, null, function (error, result) {
            if (error) throw error;
            console.log(result);
            res.json(result.insertedId);
        });
    });
})

// deleteUserByUsername
app.get('/users/deleteUserByUsername/:username', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("users").deleteMany({ username: req.params.username }, function (error, result) {
            if (error) throw error;
            res.json(result.deletedCount);
        });
    });
})

// updateUser


// BIDS

// getAllBids
app.get('/bids', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("bids").find({}).toArray(function (error, result) {
            if (error) throw error;
            res.json(result);
        });
    });
})

// getBidByBidderUsername
app.get('/bids/getBidByBidderUsername/:username', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("bids").find({ bidderUsername: req.params.username }).toArray(function (error, result) {
            if (error) throw error;
            res.json(result);
        });
    });
})

// getBidByIdAuction
app.get('/bids/getBidByIdAuction/:idAuction', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("bids").findOne({ idAuction: req.params.idAuction }, function (error, result) {
            if (error) throw error;
            res.json(result);
        });
    });
})

// addNewBid
/*{ 
	"idAuction" : "5c02d7e4548b18014824c029",
	"bidderUsername" : "entrax",
    "price" : 3
}*/
app.post('/bid', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("bids").insertOne(req.body, null, function (error, result) {
            if (error) throw error;
            res.json(result.insertedId);
        });
    });
})

// deleteBidByIdAuction
app.get('/bids/deleteBidByIdAuction/:idAuction', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("bids").deleteMany({ idAuction: req.params.idAuction }, function (error, result) {
            if (error) throw error;
            res.json(result.deletedCount);
        });
    });
})


// AUCTION

// getAllAuctions
app.get('/auctions', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("auctions").find({}).toArray(function (error, result) {
            if (error) throw error;
            res.json(result);
        });
    });
})

// getAuctionsBySellerUsername
app.get('/auctions/getAuctionsBySellerUsername/:username', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("auctions").find({ sellerUsername: req.params.username }).toArray(function (error, result) {
            if (error) throw error;
            res.json(result);
        });
    });
})

// addNewAuction
/*{ 
	"sellerUsername" : "entrax",
	"itemName" : "Clavier d'ordinateur",
	"itemDescription" : "Un clavier d’ordinateur est une interface homme-machine munie de touches permettant à l'utilisateur d'entrer dans l'ordinateur une séquence de données, notamment textuelle.",
	"minPrice" : 1,
	"date" : "2018-12-01T18:32:03.884Z",
	"timespan" : 5,
	"photoURL" : "",
    "status" : "active"
}*/
app.post('/auction', function (req, res) {
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("auctions").insertOne(req.body, null, function (error, result) {
            if (error) throw error;
            res.json(result.insertedId);
        });
    });
})

// deleteAuctionByIdAuction
app.get('/auctions/deleteAuctionByIdAuction/:idAuction', function (req, res) {
    console.log(req.params.idAuction);
    MongoClient.connect(mongoUrl, function (error, db) {
        if (error) return funcCallback(error);

        db = db.db(dbName);
        db.collection("auctions").deleteMany({ _id: new mongodb.ObjectId(req.params.idAuction) }, function (error, result) {
            if (error) throw error;
            console.log(result);
            res.json(result.deletedCount);
        });
    });
})


//Listen
app.listen(1337, function () {
    console.log('Node Events app listening on port 1337!');
});