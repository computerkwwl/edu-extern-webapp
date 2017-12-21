create table edu_base.xb_certificate_levels (
  id int8 NOT NULL,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  begin_on date,
  end_on date,
  updated_at timestamp,
  CONSTRAINT xb_cert_levels_pkey PRIMARY KEY (id)
);

ALTER TABLE edu_base.xb_certificate_levels
    OWNER to openurp;

create table edu_base.xb_ext_exam_subjects (
  id int8 NOT NULL,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  begin_on date,
  end_on date,
  updated_at timestamp,
  CONSTRAINT xb_ext_exam_subjects_pkey PRIMARY KEY (id)
);

ALTER TABLE edu_base.xb_ext_exam_subjects
    OWNER to openurp;


create table edu_base.xb_certificate_types (
  id int8 NOT NULL,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  exam_subject_id int8 NOT NULL,
  level_id int8,
  begin_on date,
  end_on date,
  updated_at timestamp,
  CONSTRAINT xb_cert_types_pkey PRIMARY KEY (id),
  CONSTRAINT fk_7389oy88qypl4v9gudyudoi09 FOREIGN KEY (exam_subject_id) REFERENCES edu_base.xb_ext_exam_subjects (id),
  CONSTRAINT fk_7389oy88qypl4v9gudyudoi08 FOREIGN KEY (level_id) REFERENCES edu_base.xb_certificate_levels (id)
);

ALTER TABLE edu_base.xb_certificate_types
    OWNER to openurp;


create table edu_base.xb_extern_exam_times (
  id int8 NOT NULL,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  begin_on date,
  end_on date,
  updated_at timestamp,
  CONSTRAINT xb_extern_exam_times_pkey PRIMARY KEY (id)
);

ALTER TABLE edu_base.xb_extern_exam_times
    OWNER to openurp;


create table edu_extern.certificates (
  id int8 PRIMARY KEY,
  type_id int8 NOT NULL,
  division_id int8,
  exam_time_id int8 NOT NULL,
  begin_on date,
  end_on date,
  updated_at timestamp,
  FOREIGN KEY (type_id) REFERENCES edu_base.xb_certificate_types (id),
  FOREIGN KEY (division_id) REFERENCES code_gb.divisions (id),
  FOREIGN KEY (exam_time_id) REFERENCES edu_base.xb_extern_exam_times (id)
);

ALTER TABLE edu_extern.certificates
    OWNER to openurp;

create table edu_extern.xb_exam_subject_settings (
  id int8 PRIMARY KEY,
  exam_subject_id int8 NOT NULL,
  url varchar(300) NOT NULL,
  keys varchar(200) NOT NULL,
  begin_on date,
  end_on date,
  updated_at timestamp,
  FOREIGN KEY (exam_subject_id) REFERENCES edu_base.xb_ext_exam_subjects (id)
);

ALTER TABLE edu_extern.xb_exam_subject_settings
    OWNER to openurp;





























































