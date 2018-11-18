// Imports
const express = require('express');

// Instatiate server
var server = express();

//use
server.use(express.json());

//tabs ///////////////////////////////////////////////////////////////////////////
var utilisateurs = [
    {
        idutilisateur:1,
        nom:"Lebreton",
        prenom:"Alexis",
        mail:"alexis.c.lebreton@gmail.com",
        login:"test",
        motdepasse:"test",
        adresse:"17 rue Montoulieu Vélane 31000 Toulouse",
        photoprofil:""
    },
    { 
        idutilisateur:2,
        nom:"Fines",
        prenom:"Guillaume",
        mail:"fine.guillaume@gmail.com",
        login:"test2",
        motdepasse:"test2",
        adresse:"18 rue Montoulieu Vélane 31000 Toulouse",
        photoprofil:""
    },
    { 
        idutilisateur:3,
        nom:"Gonord",
        prenom:"Pablo",
        mail:"pablo.gonord@gmail.com",
        login:"test3",
        motdepasse:"test3",
        adresse:"5 rue Montoulieu Vélane 31000 Toulouse",
        photoprofil:""
    }
]

var annonce = [
    {
        idannonce:"1",
        idutilisateur:"1",
        intitule:"chaise longue",
        description:"tres bon etat, tres peu servi",
        prixplanche:"4.6",
        datecreation:"2012-04-23T18:25:43.511Z",
        photobien:"",
        etat:"cloturée"
     }
]

var enchere = [
    {
        idenchere:"1",
        idutilisateur:"2",
        idannonce:"1",
        prixpropose:"5.5"
    },
    {
        idenchere:"2",
        idutilisateur:"3",
        idannonce:"1",
        prixpropose:"6.7"
    }
]

//functions /////////////////////////////////////////////////////////////////////////////





// Configure routes //////////////////////////////////////////////////////////////////////

server.get('/', function (req,res) {
    res.setHeader('Content-Type','text/html');
    res.status(200).send('<h1>Bienvenue sur mon server API REST ebay</h1>');
})

// utilisateurs api

server.get('/utilisateurs', function (req,res) {
    res.status(200).send(utilisateurs);
})

server.get('/utilisateurs/:idutilisateur', function (req,res) {
    const utilisateur = utilisateurs.find(u => u.idutilisateur === parseInt(req.params.idutilisateur));
    if (!utilisateur){
        res.status(404).send('utilisateur inconnu')
    }
    res.status(200).send(utilisateur);
})

server.post('/utilisateurs', function (req,res) {
    const utilisateur = {
        idutilisateur: utilisateurs.length + 1,
        nom : req.body.nom,
        prenom : req.body.prenom,
        mail:req.body.mail,
        login:req.body.login,
        motdepasse:req.body.motdepasse,
        adresse:req.body.prenom,
        photoprofil:req.body.photoprofil
    }
    utilisateurs.push(utilisateur);
    res.send(utilisateur);
}) 

// annonce api

server.get('/annonces', function (req,res) {
    res.status(200).send(annonces);
})

server.get('/annonces/annonce/:idannonce', function (req,res) {
    const annonce = annonces.find(u => u.idannonce === parseInt(req.params.idannonce));
    if (!annonce){
        res.status(404).send('annonce inconnu')
    }
    res.status(200).send(annonce);
})

server.get('/annonces/utilisateur/:idutilisateur', function (req,res) {
    const listannonces = annonces.find(u => u.idutilisateur === parseInt(req.params.idutilisateur));
    res.status(200).send(listannonces);
})

server.post('/annonces', function (req,res) {
    const annonce = {
        idannonce: annonces.length + 1,
        idutilisateur:req.body.idutilisateur,
        intitule:req.body.intitule,
        description:req.body.description,
        prixplanche:req.body.prixplanche,
        datecreation:req.body.datecreation,
        photobien:req.body.photobien,
        etat:req.body.etat
    }
    annonces.push(annonce);
    res.send(annonce);
}) 

//TODO le reste

// Launch server
server.listen(8080, function() {
    console.log('Server en écoute :)')
})