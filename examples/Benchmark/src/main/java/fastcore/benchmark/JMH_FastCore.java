package fastcore.benchmark;

import fastcore.FastCore;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.foreign.MemorySegment;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class JMH_FastCore {

    private long sampleNativeAddress;

    @Setup
    public void setup() {
        // Sample aligned 64-bit simulated pointer address
        sampleNativeAddress = 0x7FFE00001000L;
    }

    @Benchmark
    public void benchmarkPlatformDetection(Blackhole bh) {
        bh.consume(FastCore.isWindows());
        bh.consume(FastCore.getArch());
    }

    @Benchmark
    public void benchmarkLibraryFileNameGeneration(Blackhole bh) {
        bh.consume(FastCore.getLibraryFileName("fastaimodel"));
    }

    @Benchmark
    public void benchmarkFfmMemorySegmentConversion(Blackhole bh) {
        MemorySegment seg = FastCore.asMemorySegment(sampleNativeAddress, 1024L);
        bh.consume(seg.address());
    }

    @Benchmark
    public void benchmarkFfmLinkerQuery(Blackhole bh) {
        bh.consume(FastCore.getNativeLinker());
    }
}
