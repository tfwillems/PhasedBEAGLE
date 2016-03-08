JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

BEAGLE_UTIL_CLASSES = beagleutil/ChromIds.java beagleutil/ChromInterval.java beagleutil/Ids.java beagleutil/IntInterval.java beagleutil/Phase.java beagleutil/SampleIds.java beagleutil/Samples.java 

BLB_UTIL_CLASSES = blbutil/Const.java blbutil/FileIterator.java blbutil/FileUtil.java blbutil/Filter.java blbutil/FilterUtils.java blbutil/IndexMap.java blbutil/IndexSet.java blbutil/InputIterator.java blbutil/IntList.java blbutil/IntPair.java blbutil/Pair.java blbutil/SampleFileIterator.java blbutil/StringUtil.java blbutil/Utilities.java blbutil/Validate.java 

DAG_CLASSES = dag/Dag.java dag/DagUtils.java dag/ImmutableDagLevel.java dag/MergeableDag.java dag/Score.java dag/DagLevel.java dag/ImmutableDag.java dag/LinkageEquilibriumDag.java dag/MergeableDagLevel.java

HAPLOTYPE_CLASSES = haplotype/BasicHapPairs.java haplotype/ConsensusPhasing.java haplotype/HapsMarker.java haplotype/RevHapPair.java haplotype/Weights.java haplotype/BasicSampleHapPairs.java haplotype/HapPair.java haplotype/HapsMarkerIterator.java haplotype/SampleHapPairs.java haplotype/WrappedHapPair.java haplotype/BitHapPair.java haplotype/HapPairs.java haplotype/RefHapPairs.java haplotype/SampleHapPairsSplicer.java

IBD_CLASSES = ibd/HapSegment.java ibd/HaploidIbd.java ibd/Haplotype.java ibd/IbdBaum.java ibd/IbdSegment.java ibd/IbsHapSegments.java

MAIN_CLASSES = main/BasicGenotypeValues.java main/FixedGenotypeValues.java main/GeneticMap.java main/GenotypeValues.java main/GprobsStatistics.java main/HapPairSampler.java main/Logger.java main/Main.java main/MainHelper.java main/NuclearFamilies.java main/Parameters.java main/RestrictedGenotypeValues.java main/RevGenotypeValues.java main/RunStats.java main/SampleGenotypeValues.java main/WindowWriter.java

SAMPLE_CLASSES = sample/DuoBaum.java sample/DuoBaumLevel.java sample/DuoNodes.java sample/HapBaum.java sample/HapBaumLevel.java sample/HapNodes.java sample/ProduceHapSamples.java sample/ProduceSingleSamples.java sample/SingleBaum.java sample/SingleBaumInterface.java sample/SingleBaumLevel.java sample/SingleNodes.java sample/TrioBaum.java sample/TrioBaumLevel.java sample/TrioNodes.java

VCF_CLASSES = vcf/AL.java vcf/AllData.java vcf/BasicGL.java vcf/BitSetGT.java vcf/BitSetRefGT.java vcf/Data.java vcf/FilteredVcfIterator.java vcf/GL.java vcf/HapAL.java vcf/HbdAL.java vcf/ImputationGL.java vcf/IntervalVcfIterator.java vcf/Marker.java vcf/MarkerFilterUtils.java vcf/Markers.java vcf/MedMemGL.java vcf/MedMemGTGL.java vcf/NoPhaseGL.java vcf/NonRefData.java vcf/RefGL.java vcf/RevAL.java vcf/RevGL.java vcf/VcfEmission.java vcf/VcfEmissionFactory.java vcf/VcfEmissionIterator.java vcf/VcfHeader.java vcf/VcfIterator.java vcf/VcfMetaInfo.java vcf/VcfRecord.java vcf/VcfRefIterator.java vcf/VcfWindow.java vcf/VcfWriter.java vcf/PGPRefGT.java

SAMTOOLS_CLASSES = net/sf/samtools/Defaults.java net/sf/samtools/FileTruncatedException.java net/sf/samtools/SAMException.java net/sf/samtools/SAMFormatException.java net/sf/samtools/util/BinaryCodec.java net/sf/samtools/util/BlockCompressedFilePointerUtil.java net/sf/samtools/util/BlockCompressedInputStream.java net/sf/samtools/util/BlockCompressedOutputStream.java net/sf/samtools/util/BlockCompressedStreamConstants.java net/sf/samtools/util/BlockGunzipper.java net/sf/samtools/util/HttpUtils.java net/sf/samtools/util/IOUtil.java net/sf/samtools/util/RuntimeEOFException.java net/sf/samtools/util/RuntimeIOException.java net/sf/samtools/util/SeekableBufferedStream.java net/sf/samtools/util/SeekableFileStream.java net/sf/samtools/util/SeekableHTTPStream.java net/sf/samtools/util/SeekableStream.java net/sf/samtools/util/StringUtil.java

default: classes PhasedBeagle

classes: $(BEAGLE_UTIL_CLASSES:.java=.class) $(BLB_UTIL_CLASSES:.java=.class) $(DAG_CLASSES:.java=.class) $(HAPLOTYPE_CLASSES:.java=.class) $(IBD_CLASSES:.java=.class) $(SAMPLE_CLASSES:.java=.class) $(SAMTOOLS_CLASSES:.java=.class) $(VCF_CLASSES:.java=.class) $(MAIN_CLASSES:.java=.class)

clean:
	$(RM) beagleutil/*.class blbutil/*.class dag/*.class haplotyple/*.class ibd/*.class main/*.class sample/*.class vcf/*.class net/sf/samtools/*.class net/sf/samtools/util/*.class PhasedBEAGLE.jar

PhasedBeagle: classes
	jar cmf META-INF/MANIFEST.MF PhasedBEAGLE.jar beagleutil blbutil dag haplotype ibd main net sample vcf  
