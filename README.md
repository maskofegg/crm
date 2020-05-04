
docker run -d -p 1521:1521 -p 81:81 -v $PWD/db:/opt/h2-data -e H2_OPTIONS='-ifNotExists' --name=crm-db oscarfonts/h2