# $name$

![](https://img.shields.io/badge/coverage-100%25-green.svg)

A pure FP micro service

## Getting Started

:secret: Please refer to [Wiki](https://github.com/MYOB-Technology/monitor-slo-endpoints-api/wiki) before contributing code.

### Start Server

```bash
sbt run
```

or simply from docker

```
docker-compose up app
```

visit :point-right: http://localhost:8081/info

### Run tests

- run in local
```bash
sbt test
```

- run in docker
```bash
docker-compose run test
```

## References
- https://finagle.github.io/finch/user-guide.html
- https://typelevel.org/cats/datatypes/freemonad.html
- https://github.com/milessabin/shapeless
- http://www.staff.science.uu.nl/~swier004/publications/2008-jfp.pdf
