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

CREATE TABLE `rssdata`.`regsites` (
  `site_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `target_url` VARCHAR(512) NOT NULL,
  `check_interval_min` INTEGER UNSIGNED NOT NULL,
  `name` VARCHAR(128),
  `reg_date` TIMESTAMP DEFAULT current_timestamp,
  `latest_date` TIMESTAMP,
  `reg_user` VARCHAR(45),
  `check_status` INTEGER,
  PRIMARY KEY (`site_id`)
)
ENGINE = InnoDB;