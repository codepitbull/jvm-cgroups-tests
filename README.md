mvn clean package

# Crashes without OOM
docker run --cpuset-cpus="0" --rm -m128M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup_pre.sh

# Crashes with OOM
docker run --cpuset-cpus="0" --rm -m128M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup.sh


# TEST-runs
docker run --cpuset-cpus="0" --rm -m128M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup.sh
- Metaspace allocation disabled
- OffHeap allocation disabled
=> OOM


docker run --cpuset-cpus="0" --rm -m128M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup.sh
- Metaspace allocation disabled
- OffHeap allocation enabled and allocated BEFORE heap consumption
=> OOM


docker run --cpuset-cpus="0" --rm -m128M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup.sh
- Metaspace allocation disabled
- OffHeap allocation enabled and allocated BEFORE heap consumption
=> OOM


docker run --cpuset-cpus="0" --rm -m128M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup.sh
- Metaspace allocation disabled
- OffHeap allocation enabled and allocated together with heap consumption
=> OOM


docker run --cpuset-cpus="0" --rm -m128M --name jvm-cgroups-tests --memory-swappiness 0 --memory-swap 0 jvm-cgroups-tests:latest /app/cgroup.sh
- Metaspace allocation enabled and allocated BEFORE heap consumption
- OffHeap allocation disabled
=> Killed by Docker 