.PHONY: db run-prod

db:
	docker run -d -it --rm \
		--name ledger-db \
		-e POSTGRES_DB=ledger \
		-e POSTGRES_USER=ledgeruser \
		-e POSTGRES_PASSWORD=ledgerpass \
		-p 5432:5432 \
		-v ./data:/var/lib/postgresql/data \
		postgres:16-alpine

run-prod:
	. ./.env && docker compose up --build
