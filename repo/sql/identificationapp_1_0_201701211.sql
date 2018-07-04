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
  begin_on date NOT NULL,
  end_on date,
  updated_at timestamp NOT NULL,
  FOREIGN KEY (exam_subject_id) REFERENCES edu_base.xb_ext_exam_subjects (id)
);

ALTER TABLE edu_extern.xb_exam_subject_settings
    OWNER to openurp;


create table edu_extern.xb_exam_subject_fields (
  id int8 PRIMARY KEY,
  outer_field varchar(100) NOT NULL,
  inner_field varchar(100) NOT NULL,
  name varchar(100) NOT NULL
);

ALTER TABLE edu_extern.xb_exam_subject_fields
    OWNER to openurp;


create table edu_extern.xb_e_s_settings_req_fields (
  setting_id int8 NOT NULL,
  field_id int8 NOT NULL,
  FOREIGN KEY (setting_id) REFERENCES edu_extern.xb_exam_subject_settings (id),
  FOREIGN KEY (field_id) REFERENCES edu_extern.xb_exam_subject_fields (id)
);

ALTER TABLE edu_extern.xb_e_s_settings_req_fields
    OWNER to openurp;


create table edu_extern.xb_e_s_settings_res_fields (
  setting_id int8 NOT NULL,
  field_id int8 NOT NULL,
  FOREIGN KEY (setting_id) REFERENCES edu_extern.xb_exam_subject_settings (id),
  FOREIGN KEY (field_id) REFERENCES edu_extern.xb_exam_subject_fields (id)
);

ALTER TABLE edu_extern.xb_e_s_settings_res_fields
    OWNER to openurp;



create table edu_extern.cert_scores (
  id int8 PRIMARY KEY,
  exam_subject_id int8 NOT NULL,
  cert_type_id int8,
  cert_level_id int8,
  division_id int8,
  exam_time_id int8,
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


create table edu_extern.cert_score_courses (
  id int8 PRIMARY KEY,
  cert_score_id int8 NOT NULL,
  course_id int8 NOT NULL,
  score float NOT NULL,
  score_value varchar(200) NOT NULL,
  FOREIGN KEY (cert_score_id) REFERENCES edu_extern.cert_scores (id),
  FOREIGN KEY (course_id) REFERENCES edu_base.courses (id)
);

ALTER TABLE edu_extern.cert_score_courses
    OWNER to openurp;


--drop table edu_extern.certification_courses cascade; drop table edu_extern.certifications cascade;
create table edu_extern.certifications (
  id int8 PRIMARY KEY,
  std_id int8 NOT NULL,
  certificate_id int8 NOT NULL,
  code varchar(200) NOT NULL,
  score varchar(200) NOT NULL,
  happen_by varchar(200) NOT NULL,
  happen_at timestamp NOT NULL,
  passed boolean,
  pass_by varchar(200),
  pass_at timestamp,
  FOREIGN KEY (std_id) REFERENCES edu_base.students (id),
  FOREIGN KEY (certificate_id) REFERENCES edu_extern.certificates (id)
);

ALTER TABLE edu_extern.certifications
    OWNER to openurp;



create table edu_extern.certification_courses (
  id int8 PRIMARY KEY,
  certification_id int8 NOT NULL,
  course_id int8 NOT NULL,
  score float NOT NULL,
  score_value varchar(200) NOT NULL,
  FOREIGN KEY (certification_id) REFERENCES edu_extern.certifications (id),
  FOREIGN KEY (course_id) REFERENCES edu_base.courses (id)
);

ALTER TABLE edu_extern.certification_courses
    OWNER to openurp;
