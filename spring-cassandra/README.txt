# DockerでのCassandra環境準備
## Cassandra構築
$ docker run --name cassandra -d cassandra:latest
$ docker ps
$ docker stop <CONTAINER_ID>
$ docker run -d -p 9042:9042 cassandra

## CQL実行
$ docker run -it --name cassandra-cqlsh --link nervous_lalande:cassandra --rm cassandra sh -c 'exec cqlsh "$CASSANDRA_PORT_9042_TCP_ADDR"'