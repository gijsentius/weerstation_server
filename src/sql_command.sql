# noinspection SqlResolveForFile

CREATE TABLE `measurements`
(
  `STN` INT(10) UNSIGNED NOT NULL ,
  `TIMESTAMP` TIMESTAMP ,
  `TEMP` FLOAT ,
  `DEWP` FLOAT ,
  `STP` FLOAT ,
  `SLP` FLOAT ,
  `VISIB` FLOAT ,
  `WDSP` FLOAT ,
  `PRCP` FLOAT ,
  `SNDP` FLOAT ,
  `FRSHTT` VARCHAR(6) ,
  `CLDC` FLOAT ,
  `WNDDIR` INT ,
  FOREIGN KEY (STN) REFERENCES stations(stn)
)
  ENGINE = InnoDB;