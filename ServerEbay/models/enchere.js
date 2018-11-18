'use strict';
module.exports = (sequelize, DataTypes) => {
  const Enchere = sequelize.define('Enchere', {
    idUTILISATEURS: DataTypes.INTEGER,
    intitule: DataTypes.STRING,
    description: DataTypes.STRING,
    prixplancher: DataTypes.FLOAT,
    datecreation: DataTypes.DATE,
    etat: DataTypes.BOOLEAN,
    photobien: DataTypes.STRING
  }, {});
  Enchere.associate = function(models) {
    // associations can be defined here
    models.Enchere.belongsto(models.User, {
      foreignKey: {
        allowNull: false
      }
    })
  };
  return Enchere;
};