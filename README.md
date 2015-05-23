# PhasedBEAGLE
BEAGLE is a fantastic imputation tool for SNPs and multiallelic markers. Unfortunately, the current version of BEAGLE
does not report phased genotype posteriors. To address this issue, this repository
alters some of the internals of the original program so that it can also return phased
genotype posteriors that can be used as input to other analyses
