#!/bin/bash

__standalone_tmux() {
  docker-compose build
  type tmuxinator > /dev/null 2>&1 || brew install tmuxinator
  touch ~/.tmux.conf
  tmuxinator local || tmuxinator start -p .tmuxinator_default.yml
}

__standalone_tmux
