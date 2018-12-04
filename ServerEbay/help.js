// Imports
var MongoClient = require('mongodb').MongoClient;

// Connexion
MongoClient.connect("mongodb://localhost:27017/AndroidEbayDB", function (error, db) {
    if (error) return funcCallback(error);

    console.log("Connecté à la base de données 'AndroidEbayDB'");
});

// Créer une collection
db.createCollection("customers", function(err, res) {
	if (err) throw err;
	console.log("Collection created!");
});

// Récupérer tous les documents d'une collection
db.collection("personnages").find().toArray(function (error, results) {
    if (error) throw error;

    results.forEach(function (i, obj) {
        console.log(
            "ID : " + obj._id.toString() + "\n" // 53dfe7bbfd06f94c156ee96e
			"Nom : " + obj.name + "\n"           // Adrian Shephard
			"Jeu : " + obj.game                  // Half-Life: Opposing Force
        );
    });
});

// Récupérer le document correspondant à un identifiant
var MongoObjectID = require("mongodb").ObjectID;          // Il nous faut ObjectID
var idToFind = "53dfe7bbfd06f94c156ee96e";           // Identifiant, sous forme de texte
var objToFind = { _id: new MongoObjectID(idToFind) }; // Objet qui va nous servir pour effectuer la recherche

db.collection("personnages").findOne(objToFind, function (error, result) {
    if (error) throw error;

    console.log(
        "ID : " + result._id.toString() + "\n" // 53dfe7bbfd06f94c156ee96e
        "Nom : " + result.name + "\n"           // Adrian Shephard
        "Jeu : " + result.game                  // Half-Life: Opposing Force
    );
});

// Insérer un document
var objNew = { name: "GLaDOS", game: "Portal" };

db.collection("personnages").insert(objNew, null, function (error, results) {
    if (error) throw error;

    console.log("Le document a bien été inséré");
});

// Insérer et/ou éditer
db.collection("personnages").save(objNew, { w: 1 }); // Ce document sera inséré

// Éditer un document
// Exemple 1 : remplacement
db.collection("personnages").update(
    { name: "GladOS" },
    { name: "GladOS", game: "Portal 2" }
);

// Exemple 2 : mise à jour, via $set
db.collection("personnages").update(
    { name: "GladOS" },
    { $set: { game: "Portal 2" } }
);

// Supprimer un document
var MongoObjectID = require("mongodb").ObjectID;          // Il nous faut ObjectID
var idToFind = "53dfe7bbfd06f94c156ee96e";           // Identifiant, sous forme de texte
var objToFind = { _id: new MongoObjectID(idToFind) }; // Objet qui va nous servir pour effectuer la recherche

db.collection("personnages").remove(objToFind, null, function (error, result) {
    if (error) throw error;
});
