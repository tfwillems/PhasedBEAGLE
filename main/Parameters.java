/*
 * Copyright (C) 2014 Brian L. Browning
 *
 * This file is part of Beagle
 *
 * Beagle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beagle is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import blbutil.Const;
import blbutil.Validate;
import java.io.File;
import java.util.Map;

/**
 * <p>Class {@code Parameters} represents the parameters for a Beagle analysis.
 * </p>
 * Instances of class {@code Parameters} are immutable.
 *
 * @author Brian L. Browning {@code <browning@uw.edu>}
 */
public final class Parameters {

    private final String[] args;

    // data input/output parameters
    private final File gt;
    private final File gl;
    private final File gtgl;
    private final File ref;
    private final String out;
    private final File excludesamples;
    private final File excludemarkers;
    private final File ped;
    private final String chrom;
    private final float maxlr;
    private final boolean outputsnps;

    // algorithm parameters
    private final int nthreads;
    private final int window;
    private final int overlap;
    private final boolean gprobs;
    private final boolean impute;
    private final boolean usephase;
    private final float singlescale;
    private final float duoscale;
    private final float trioscale;
    private final int burnin_its;
    private final int phase_its;
    private final int impute_its;
    private final long seed;

    // ibd parameters
    private final boolean ibd;
    private final float ibdlod;
    private final float ibdscale;
    private final int ibdtrim;
    private final File map;

    // expert parameters
    private final int nsamples;
    private final int buildwindow;

    /**
     * Constructs a new {@code Parameters} instance.
     * @param args the Beagle command line arguments.
     * @throws IllegalArgumentException if the command line arguments
     * are incorrectly specified.
     */
    public Parameters(String[] args) {

        int IMAX = Integer.MAX_VALUE;
        long LMIN = Long.MIN_VALUE;
        long LMAX = Long.MAX_VALUE;
        float FMIN = Float.MIN_VALUE;
        float FMAX = Float.MAX_VALUE;

        this.args = args.clone();
        Map<String, String> argsMap = Validate.argsToMap(args, '=');

        // data input/output parameters
        gt = Validate.getFile(
                Validate.stringArg("gt", argsMap, false, null, null));
        gl = Validate.getFile(
                Validate.stringArg("gl", argsMap, false, null, null));
        gtgl = Validate.getFile(
                Validate.stringArg("gtgl", argsMap, false, null, null));
        ref = Validate.getFile(
                Validate.stringArg("ref", argsMap, false, null, null));
        out = Validate.stringArg("out", argsMap, true, null, null);
        excludesamples = Validate.getFile(
                Validate.stringArg("excludesamples", argsMap, false, null, null));
        excludemarkers = Validate.getFile(
                Validate.stringArg("excludemarkers", argsMap, false, null, null));
        ped = Validate.getFile(
                Validate.stringArg("ped", argsMap, false, null, null));
        chrom = Validate.stringArg("chrom", argsMap, false, null, null);
        maxlr = Validate.floatArg("maxlr", argsMap, false, 5000.0f, 1.1f, FMAX);
	outputsnps = Validate.booleanArg("snpsout", argsMap, false, true);

        // algorithm parameters
        window = Validate.intArg("window", argsMap, false, 50000, 1, IMAX);
        overlap = Validate.intArg("overlap", argsMap, false, 3000, 0, IMAX);
        gprobs = Validate.booleanArg("gprobs", argsMap, false, true);
        impute = Validate.booleanArg("impute", argsMap, false, true);
        usephase=Validate.booleanArg("usephase", argsMap, false, false);
        singlescale = Validate.floatArg("singlescale", argsMap, false, 0.8f, FMIN, FMAX);
        duoscale = Validate.floatArg("duoscale", argsMap, false, 1.0f, FMIN, FMAX);
        trioscale = Validate.floatArg("trioscale", argsMap, false, 1.0f, FMIN, FMAX);
        burnin_its = Validate.intArg("burnin-its", argsMap, false, 5, 0, IMAX);
        phase_its = Validate.intArg("phase-its", argsMap, false, 5, 0, IMAX);
        impute_its = Validate.intArg("impute-its", argsMap, false, 5, 0, IMAX);
        nthreads = Validate.intArg("nthreads", argsMap, false, 1, 1, 100000);
        seed = Validate.longArg("seed", argsMap, false, -99999, LMIN, LMAX);

        // ibd parameters
        ibd = Validate.booleanArg("ibd", argsMap, false, false);
        ibdlod = Validate.floatArg("ibdlod", argsMap, false, 3.0f, FMIN, FMAX);
        ibdscale = Validate.floatArg("ibdscale", argsMap, false, 0.0f, 0.0f, FMAX);
        ibdtrim = Validate.intArg("ibdtrim", argsMap, false, 40, 0, IMAX);
        map = Validate.getFile(Validate.stringArg("map", argsMap, false, null, null));

        // expert parameters
        nsamples = Validate.intArg("nsamples", argsMap, false, 4, 1, IMAX);
        buildwindow = Validate.intArg("buildwindow", argsMap, false, 1200, 1, IMAX);

        Validate.confirmEmptyMap(argsMap);
    }

    /**
     * Returns the Beagle command line arguments.
     * @return the Beagle command line arguments.
     */
    public String[] args() {
        return args.clone();
    }

    /**
     * Returns a string summary of the Beagle command line arguments.
     * @return a string summary of the Beagle command line arguments.
     */
    public static String usage() {
        String nl = Const.nl;
        return  "Command line syntax: java -jar PhasedBEAGLE.jar [arguments]" + nl
                + nl
                + "data input/output parameters ..." + nl
                + "  gt=<VCF file: use GT field>                        (optional)" + nl
                + "  gl=<VCF file: use GL/PL field>                     (optional)" + nl
                + "  gtgl=<VCF file: use GT and GL/PL fields>           (optional)" + nl
                + "  ref=<VCF file with phased genotypes>               (optional)" + nl
                + "  out=<output file prefix>                           (required)" + nl
                + "  excludesamples=<file with 1 sample ID per line>    (optional)" + nl
                + "  excludemarkers=<file with 1 marker ID per line>    (optional)" + nl
                + "  ped=<linkage format pedigree file>                 (optional)" + nl
                + "  chrom=<[chrom] or [chrom]:[start]-[end]>           (optional)" + nl
                + "  maxlr=<max GL/PL likelihood ratio>                 (default=5000)" + nl
	        + "  snpsout=<output snp records (true/false)>          (default=true)" + nl + nl

                + "algorithm parameters ..." + nl
                + "  nthreads=<number of threads>                       (default=1)" + nl
                + "  window=<markers per window>                        (default=50000)" + nl
                + "  overlap=<overlap between windows>                  (default=3000)" + nl
                + "  gprobs=<print GP field (true/false)>               (default=true)" + nl
                + "  impute=<impute ungenotyped variants (true/false)>  (default=true)" + nl
                + "  usephase=<use phase in \"gt\" or \"gtgl\" file>        (default=false)" + nl
                + "  singlescale=<model scale for singles>              (default=1.0)" + nl
                + "  duoscale=<model scale for duos>                    (default=1.0)" + nl
                + "  trioscale=<model scale trios>                      (default=1.0)" + nl
                + "  burnin-its=<number of iterations>                  (default=5)" + nl
                + "  phase-its=<number of iterations>                   (default=5)" + nl
                + "  impute-its=<number of iterations>                  (default=5)" + nl
                + "  seed=<random seed>                                 (default=-99999)" + nl + nl

                + "IBD parameters ..." + nl
                + "  ibd=<perform IBD detection (true/false)>           (default=false)" + nl
                + "  ibdlod=<min LOD score for reporting IBD>           (default=3.0)" + nl
                + "  ibdscale=<model scale factor for Refined IBD>      (default: data-dependent)" + nl
                + "  ibdtrim=<markers at each segment end>              (default=40)" + nl;
    }

    /**
     * Returns a sample-size-adjusted IBD scale parameter. Returns
     * {@code this.ibdscale()} if {@code this.ibdscale()!=0.0f}, and
     * returns {@code Math.max(2.0f, (float) Math.sqrt(nSamples/100.0))}
     * otherwise.
     *
     * @param nSamples the number of samples.
     * @return a sample-size-adjusted IBD scale parameter.
     * @throws IllegalArgumentException if {@code nSamples<0}.
     */
    public float adjustedIbdScale(int nSamples) {
        if (nSamples <= 0) {
            throw new IllegalArgumentException(String.valueOf(nSamples));
        }
        if (ibdscale==0) {
            return Math.max(2.0f, (float) Math.sqrt(nSamples/100.0));
        }
        else {
            return ibdscale;
        }
    }

    // data input/output parameters

    /**
     * Returns the gt parameter or {@code null} if no gt parameter was
     * specified.
     * @return the gt parameter or {@code null} if no gt parameter was
     * specified.
     */
    public File gt() {
        return gt;
    }

    /**
     * Returns the gl parameter or {@code null} if no gl parameter was
     * specified.
     * @return the gl parameter or {@code null} if no gl parameter was
     * specified.
     */
    public File gl() {
        return gl;
    }

    /**
     * Returns the gtgl parameter or {@code null} if no gtgl parameter was
     * specified.
     * @return the gtgl parameter or {@code null} if no gtgl parameter was
     * specified.
     */
    public File gtgl() {
        return gtgl;
    }

    /**
     * Returns the ref parameter or {@code null} if no ref parameter was
     * specified.
     * @return the ref parameter or {@code null} if no ref parameter was
     * specified.
     */
    public File ref() {
        return ref;
    }

    /**
     * Returns the out parameter.
     * @return the out parameter.
     */
    public String out() {
        return out;
    }

    /**
     * Returns the excludesamples parameter or {@code null}
     * if no excludesamples parameter was specified.
     *
     * @return the excludesamples parameter or {@code null}
     * if no excludesamples parameter was specified.
     */
    public File excludesamples() {
        return excludesamples;
    }

    /**
     * Returns the excludemarkers parameter or {@code null}
     * if no excludemarkers parameter was specified.
     *
     * @return the excludemarkers parameter or {@code null}
     * if no excludemarkers parameter was specified.
     */
    public File excludemarkers() {
        return excludemarkers;
    }

    /**
     * Returns the ped parameter or {@code null}
     * if no ped parameter was specified.
     *
     * @return the ped parameter or {@code null}
     * if no ped parameter was specified.
     */
    public File ped() {
        return ped;
    }

    /**
     * Returns the chrom parameter or {@code null}
     * if no chrom parameter was specified.
     *
     * @return the chrom parameter or {@code null}
     * if no chrom parameter was specified.
     */
    public String chrom() {
        return chrom;
    }

    /**
     * Returns the maxlr parameter.
     * @return the maxlr parameter
     */
    public float maxlr() {
        return maxlr;
    }

    /**
     * Returns the outputsnps parameter.
     * @return the outputsnps parameter.
     */
    public boolean outputsnps(){
	return outputsnps;
    }

    // algorithm parameters

    /**
     * Returns the nthreads parameter.
     * @return the nthreads parameter.
     */
    public int nthreads() {
        return nthreads;
    }

    /**
     * Returns the window parameter.
     * @return the window parameter.
     */
    public int window() {
        return window;
    }

    /**
     * Return the overlap parameter.
     * @return the overlap parameter.
     */
    public int overlap() {
        return overlap;
    }

    /**
     * Returns the gprobs parameter.
     * @return the gprobs parameter.
     */
    public boolean gprobs() {
        return gprobs;
    }

    /**
     * Returns the impute parameter.
     * @return the impute parameter.
     */
    public boolean impute() {
        return impute;
    }

    /**
     * Returns the usephase parameter.
     * @return the usephase parameter.
     */
    public boolean usephase() {
        return usephase;
    }

    /**
     * Returns the singlescale parameter.
     * @return the singlescale parameter.
     */
    public float singlescale() {
        return singlescale;
    }

    /**
     * Returns the duocale parameter.
     * @return the duoscale parameter.
     */
    public float duoscale() {
        return duoscale;
    }

    /**
     * Returns the trioscale parameter.
     * @return the trioscale parameter.
     */
    public float trioscale() {
        return trioscale;
    }

    /**
     * Returns the burnin-its parameter.
     * @return the burnin-its parameter.
     */
    public int burnin_its() {
        return burnin_its;
    }

    /**
     * Returns the phase-its parameter.
     * @return the phase-its parameter.
     */
    public int phase_its() {
        return phase_its;
    }

    /**
     * Returns the impute-its parameter.
     * @return the impute-its parameter.
     */
    public int impute_its() {
        return impute_its;
    }

    /**
     * Returns the seed parameter.
     * @return the seed parameter.
     */
    public long seed() {
        return seed;
    }

    // ibd parameters

    /**
     * Returns the ibd parameter.
     * @return the ibd parameter.
     */
    public boolean ibd() {
        return ibd;
    }

    /**
     * Returns the ibdlod parameter.
     * @return the ibdlod parameter
     */
    public float ibdlod() {
        return ibdlod;
    }

    /**
     * Returns the ibdscale parameter.
     * @return the ibdscale parameter.
     */
    public float ibdscale() {
        return ibdscale;
    }

    /**
     * Returns the ibdtrim parameter.
     * @return the ibdtrim parameter.
     */
    public int ibdtrim() {
        return ibdtrim;
    }

    /**
     * Returns the map parameter.
     * @return the map parameter.
     */
    public File map() {
        return map;
    }

    // expert parameters

    /**
     * Return the nsamples parameter.
     * @return the nsamples parameter.
     */
    public int nsamples() {
        return nsamples;
    }

    /**
     * Returns the buildwindow parameter.
     * @return the buildwindow parameter.
     */
    public int buildwindow() {
        return buildwindow;
    }
}
