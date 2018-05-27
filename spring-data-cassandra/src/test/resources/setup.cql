CREATE TABLE login_event(
  person_id text,
  event_time timestamp,
  event_code int,
  ip_address text,
  primary key (person_id, event_time))
  with CLUSTERING ORDER BY (event_time DESC)
;
INSERT INTO login_event (person_id, event_time, event_code, ip_address) VALUES ('abc', '2018-05-12T18:53:00', 999, '192.168.11.1');