APP_NAME=mangaka-backend
DOCKER_COMPOSE=docker compose

# Rebuild the Docker image and restart the container
reload:
	@echo "🔄 Rebuilding image and restarting container..."
	$(DOCKER_COMPOSE) down
	docker build -t $(APP_NAME) .
	$(DOCKER_COMPOSE) up -d --build
	@echo "✅ Reload complete."

# Shortcut to stop the containers
stop:
	$(DOCKER_COMPOSE) down

# Shortcut to start the containers without rebuild
start:
	$(DOCKER_COMPOSE) up -d

# View logs
logs:
	$(DOCKER_COMPOSE) logs -f

.PHONY: reload stop start logs
