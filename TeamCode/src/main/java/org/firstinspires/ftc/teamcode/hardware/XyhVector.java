package org.firstinspires.ftc.teamcode.hardware;

    public class XyhVector {

        public double x;
        public double y;
        public double h;

        public XyhVector() {
            this(0.0, 0.0, 0.0);
        }

        public XyhVector(double x, double y, double h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }

        /** Copy constructor. */
        public XyhVector(XyhVector other) {
            this(other.x, other.y, other.h);
        }

        /** Copies another vector's values into this one (in place). */
        public void set(XyhVector other) {
            this.x = other.x;
            this.y = other.y;
            this.h = other.h;
        }

        public void set(double x, double y, double h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }

        /**
         * Wraps an angle (radians) into the range (-PI, PI]. Matches the
         * "normDiff" call at the end of the tutorial's odometry() function,
         * used to keep pos.h from growing unbounded as the robot spins.
         */
        public double normDiff(double angleRad) {
            double normalized = angleRad;
            while (normalized > Math.PI) normalized -= 2 * Math.PI;
            while (normalized <= -Math.PI) normalized += 2 * Math.PI;
            return normalized;
        }

        /** Straight-line distance from this point to another (ignores heading). */
        public double distanceTo(XyhVector other) {
            double dx = other.x - this.x;
            double dy = other.y - this.y;
            return Math.hypot(dx, dy);
        }

        /** Returns a copy of this vector. */
        public XyhVector copy() {
            return new XyhVector(this.x, this.y, this.h);
        }

        /** Heading in degrees, handy for telemetry/dashboard display. */
        public double getHeadingDegrees() {
            return Math.toDegrees(this.h);
        }

        @Override
        public String toString() {
            return String.format("XyhVector(x=%.2f, y=%.2f, h=%.2f\u00B0)", x, y, getHeadingDegrees());
        }
    }

