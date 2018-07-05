package de.innovationgate.wgpublisher;

import de.innovationgate.wgpublisher.problems.AdministrativeProblemType;
import de.innovationgate.wgpublisher.problems.GlobalScope;
import de.innovationgate.wgpublisher.problems.ProblemOccasion;
import de.innovationgate.wgpublisher.problems.ProblemScope;
import de.innovationgate.wgpublisher.problems.ProblemType;

public class UpdateConfigOccasion implements ProblemOccasion {

    @Override
    public ProblemScope getDefaultScope() {
        return GlobalScope.INSTANCE;
    }

    @Override
    public Class<? extends ProblemType> getDefaultType() {
        return AdministrativeProblemType.class;
    }

    @Override
    public Class<?> getDefaultRefClass() {
        return WGACore.class;
    }

    @Override
    public boolean isClearedAutomatically() {
        return true;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof UpdateConfigOccasion);
    }


}
