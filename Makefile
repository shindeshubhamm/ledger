.PHONY: db

db:
	docker run --name ledger-db \
		-e MYSQL_ROOT_PASSWORD=rootpassword \
		-e MYSQL_DATABASE=ledger \
		-e MYSQL_USER=ledgeruser \
		-e MYSQL_PASSWORD=ledgerpass \
		-p 3307:3306 \
		-v ./data:/var/lib/mysql \
		-d mysql:8.0