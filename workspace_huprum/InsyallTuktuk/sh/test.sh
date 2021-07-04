#!/bin/bash

if cat /etc/*release*  | grep 'Ubuntu 20.04'; then
  echo "It's there!"
else
  exit 1
fi
echo ok
