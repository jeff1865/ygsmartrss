CREATE TABLE `smart_agent`.`Test` (
  `name` VARCHAR(50) NOT NULL COMMENT 'naming id',
  `hitcount` INTEGER UNSIGNED NOT NULL COMMENT 'count value',
  PRIMARY KEY (`name`)
)
ENGINE = InnoDB;

ALTER TABLE `smart_agent`.`test` ADD COLUMN `hit2` INTEGER UNSIGNED AFTER `hitcount`;