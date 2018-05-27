CREATE KEYSPACE IF NOT EXISTS showcase
  WITH REPLICATION = {
    'class' : 'SimpleStrategy',
    'replication_factor' : 1
  }
;
use showcase;
CREATE TABLE login_event(
  person_id text,
  event_time timestamp,
  event_code int,
  ip_address text,
  primary key (person_id, event_time))
  with CLUSTERING ORDER BY (event_time DESC)
;