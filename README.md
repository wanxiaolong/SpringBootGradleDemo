This is a demo project to use gradle.

### For development
This project contains git hooks to check commit message and coding style, so
before developing this project, you'll need to set git hooks path.

```shell script
# tell git where to find the hooks
git config --global core.hooksPath .hooks

# enable the execution status for hook scripts
chmod +x .hooks/*
```