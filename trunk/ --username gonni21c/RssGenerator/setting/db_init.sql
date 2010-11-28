CREATE TABLE `smart_agent`.`Test` (
  `name` VARCHAR(50) NOT NULL COMMENT 'naming id',
  `hitcount` INTEGER UNSIGNED NOT NULL COMMENT 'count value',
  PRIMARY KEY (`name`)
)
ENGINE = InnoDB;

ALTER TABLE `smart_agent`.`test` ADD COLUMN `hit2` INTEGER UNSIGNED AFTER `hitcount`;

CREATE TABLE site_T0003 (
  anc_url VARCHAR(767) NOT NULL,
  anc_text VARCHAR(256) NOT NULL,
  anc_img VARCHAR(256) NOT NULL,
  dup_cnt INTEGER UNSIGNED NOT NULL,
  regDate TIMESTAMP default 0,
  latestDate TIMESTAMP default current_timestamp on update current_timestamp,
  PRIMARY KEY (anc_url)
);

