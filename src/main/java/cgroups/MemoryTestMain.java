package cgroups;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class MemoryTestMain {

	private static final int MB = 1024 * 1024;
	private static List<byte[]> heap = new ArrayList<>();
	private static List<ByteBuffer> offHeap = new ArrayList<>();
	private static List<Class<?>> metaspace = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
		System.out.println("---------------------------------------------");
		System.out.println("Available processors => " + Runtime.getRuntime().availableProcessors());
		printStats();
//		useMetaspace();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			useHeap();
			TimeUnit.SECONDS.sleep(1);
			printStats();
		}
		System.out.println("DONE");
	}

	public static void useHeap() {
		heap.add(new byte[MB * 4]);
	}

	public static void useOffHEap() {
		offHeap.add(ByteBuffer.allocateDirect(MB * 4));
	}

	public static void useMetaspace() {
		for (int i = 0; i < 10000; i++) {
			metaspace.add(new ByteBuddy()
					.subclass(Object.class)
					.method(ElementMatchers.named("toString"))
					.intercept(FixedValue.value("Hello World!"))
					.make()
					.load(MemoryTestMain.class.getClassLoader())
					.getLoaded());
		}
	}


	public static void printStats() {
		Runtime runtime = Runtime.getRuntime();
		System.out.println("Total Memory => " + runtime.totalMemory());
		System.out.println("Free Memory  => " + runtime.freeMemory());
		System.out.println("Max Memory   => " + runtime.maxMemory());
		for (MemoryPoolMXBean memoryMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
			System.out.println(memoryMXBean.getName()+" (max/used) => "+memoryMXBean.getUsage().getMax()/1024/1024+"/"+memoryMXBean.getUsage().getUsed()/1024/1024 + " mb");
		}
		System.out.println("---------------------------------------------");
	}
}
