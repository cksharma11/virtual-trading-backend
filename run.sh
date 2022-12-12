#!/bin/bash

__git_hooks() {
  cp scripts/hooks/pre-commit .git/hooks/
}

__service() {
  docker-compose build
  type tmuxinator > /dev/null 2>&1 || brew install tmuxinator
  touch ~/.tmux.conf
  tmuxinator local || tmuxinator start -p .tmuxinator_default.yml
}

__git_hooks
__service
