// Imports
var mongo = require('mongodb'); 

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("AndroidEbayDB");
  dbo.createCollection("users", function(err, res) {
    if (err) throw err;
    console.log("Collection users created!");
	db.close();
  });
  dbo.createCollection("bids", function(err, res) {
    if (err) throw err;
    console.log("Collection bids created!");
	db.close();
  });
  dbo.createCollection("announcements", function(err, res) {
    if (err) throw err;
    console.log("Collection announcements created!");
	db.close();
  });
});