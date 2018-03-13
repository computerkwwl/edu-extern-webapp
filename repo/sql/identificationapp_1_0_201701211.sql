--2017-12-11

create table edu_base.xb_certificate_levels (
  id int8 PRIMARY KEY,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL
);

ALTER TABLE edu_base.xb_certificate_levels
    OWNER to openurp;

create table edu_base.xb_ext_exam_subjects (
  id int8 PRIMARY KEY,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL
);

ALTER TABLE edu_base.xb_ext_exam_subjects
    OWNER to openurp;


create table edu_base.xb_certificate_types (
  id int8 PRIMARY KEY,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  exam_subject_id int8 NOT NULL,
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL,
  FOREIGN KEY (exam_subject_id) REFERENCES edu_base.xb_ext_exam_subjects (id)
);

ALTER TABLE edu_base.xb_certificate_types
    OWNER to openurp;


create table edu_base.xb_extern_exam_times (
  id int8 PRIMARY KEY,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  en_name varchar(100),
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL
);

ALTER TABLE edu_base.xb_extern_exam_times
    OWNER to openurp;

--drop table edu_extern.certificates cascade;
create table edu_extern.certificates (
  id int8 PRIMARY KEY,
  code varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  type_id int8 NOT NULL,
  level_id int8,
  division_id int8,
  exam_time_id int8 NOT NULL,
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL,
  FOREIGN KEY (type_id) REFERENCES edu_base.xb_certificate_types (id),
  FOREIGN KEY (level_id) REFERENCES edu_base.xb_certificate_levels (id),
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
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL,
  FOREIGN KEY (exam_subject_id) REFERENCES edu_base.xb_ext_exam_subjects (id)
);

ALTER TABLE edu_extern.xb_exam_subject_settings
    OWNER to openurp;


create table edu_extern.cert_scores (
  id int8 PRIMARY KEY,
  exam_subject_id int8 NOT NULL,
  cert_type_id int8,
  cert_level_id int8,
  division_id int8,
  exam_time_id int8,
  score float NOT NULL,
  score_value varchar(200) NOT NULL,
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL,
  FOREIGN KEY (exam_subject_id) REFERENCES edu_base.xb_ext_exam_subjects (id),
  FOREIGN KEY (cert_type_id) REFERENCES edu_base.xb_certificate_types (id),
  FOREIGN KEY (cert_level_id) REFERENCES edu_base.xb_certificate_levels (id),
  FOREIGN KEY (division_id) REFERENCES code_gb.divisions (id),
  FOREIGN KEY (exam_time_id) REFERENCES edu_base.xb_extern_exam_times (id),
  UNIQUE (exam_subject_id, cert_level_id, division_id, exam_time_id)
);

ALTER TABLE edu_extern.cert_scores
    OWNER to openurp;



create table edu_extern.cert_scores_courses (
  cert_score_id int8 NOT NULL,
  course_id int8 NOT NULL,
  PRIMARY KEY (cert_score_id, course_id),
  FOREIGN KEY (cert_score_id) REFERENCES edu_extern.cert_scores (id),
  FOREIGN KEY (course_id) REFERENCES edu_base.courses (id)
);

ALTER TABLE edu_extern.cert_scores_courses
    OWNER to openurp;






























































