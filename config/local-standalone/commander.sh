#!/usr/bin/env bash

type whiptail > /dev/null 2>&1 || brew install newt

export NEWT_COLORS='
window=,black
border=black,black
textbox=white,black
'

while true
do
    LINES=$(stty size | cut -d" " -f1)
    COLUMNS=$(stty size | cut -d" " -f2)
    choice=$(
        whiptail \
            --menu "Standalone" \
            --nocancel \
            --notags \
            $LINES $COLUMNS $(( $LINES - 8 )) \
            quit "Quit standalone" \
            detach "Detach standalone" \
            3>&1 1>&2 2>&3 # trick to swap stdout and stderr
    )

    case ${choice} in
        quit) docker-compose down && tmux kill-session -t virtual_trading;;
        detach) tmux detach-client -s virtual_trading;;
    esac
    sleep 1
done
