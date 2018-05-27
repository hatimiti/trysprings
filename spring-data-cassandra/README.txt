# DockerでのCassandra環境準備
## Cassandra構築
$ docker run --name cassandra -d cassandra:latest
$ docker ps
$ docker stop <CONTAINER_ID>

## Cassandra実行
$ docker run -d -p 9042:9042 cassandra

## CQL実行
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                                       NAMES
73ee11974769        cassandra           "docker-entrypoint.s…"   33 seconds ago      Up 35 seconds       7000-7001/tcp, 7199/tcp, 9160/tcp, 0.0.0.0:9042->9042/tcp   ecstatic_swartz

$ docker run -it --name cassandra-cqlsh --link ＜ここにNAMES＞:cassandra --rm cassandra sh -c 'exec cqlsh "$CASSANDRA_PORT_9042_TCP_ADDR"'
