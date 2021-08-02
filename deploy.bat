call mvn package -P production
call docker build -t backend .
call docker-compose -p pause_piano up -d