_create_role_and_database() {
  psql -U $(whoami) -d postgres -c "SELECT rolname FROM pg_roles WHERE rolname='infydex';" | grep -w 'infydex' ||
    psql -U $(whoami) -d postgres -c "create user infydex with encrypted password 'infydex';"

  psql -U $(whoami) -d postgres -c "SELECT rolsuper FROM pg_roles WHERE rolname='infydex';" | grep -w 't' ||
    psql -U $(whoami) -d postgres -c "ALTER USER infydex WITH SUPERUSER;"

  psql -l | grep -w virtual_trading ||
    psql -U $(whoami) -d postgres -c 'CREATE DATABASE virtual_trading OWNER infydex;'
}

dev() {
  mkdir -p "${PWD}"/.m2

  _create_role_and_database

  docker network create infydex
  docker build -t virtual_trading -f Dockerfile .
  docker-compose up
}

usage() {
  echo "use dev to start dev server"
}

CMD=${1:-}
case ${CMD} in
dev) dev ;;
*) usage ;;
esac
