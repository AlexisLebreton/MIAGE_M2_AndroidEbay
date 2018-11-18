'use strict';
module.exports = (sequelize, DataTypes) => {
  const Utilisateur = sequelize.define('Utilisateur', {
    email: DataTypes.STRING,
    nom: DataTypes.STRING,
    prenom: DataTypes.STRING,
    identifiant: DataTypes.STRING,
    motdepasse: DataTypes.STRING,
    adresse: DataTypes.STRING,
    photoprofil: DataTypes.STRING
  }, {});
  Utilisateur.associate = function(models) {
    // associations can be defined here
    models.Utilisateur.hasMany(models.Enchere)
  };
  return Utilisateur;
};