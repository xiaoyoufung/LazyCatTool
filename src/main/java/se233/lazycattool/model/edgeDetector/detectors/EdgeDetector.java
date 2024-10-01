package se233.lazycattool.model.edgeDetector.detectors;

import java.io.File;

public interface EdgeDetector {
    File detectEdges(File inputFile) throws Exception;
}
