mvn clean package

# Crashes without OOM
docker run --cpuset-cpus="0" --rm -m256M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup_pre.sh

# Crashes with OOM
docker run --cpuset-cpus="0" --rm -m256M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup.sh