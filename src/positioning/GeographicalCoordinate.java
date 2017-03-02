package positioning;

//import com.jhlabs.map.proj.Projection;
//import com.jhlabs.map.proj.ProjectionFactory;
//import com.jhlabs.point.Point2DGeneric;
import java.util.Locale;

/**
 * Representa Latitude e Longitude..
 *
 */
public class GeographicalCoordinate {

//    private final static Projection projecaoMercator = ProjectionFactory.
//            fromPROJ4Specification(new String[]{"+proj=merc", "+ellps=WGS84", "+k=6378137"});
    private double latitude;
    private double longitude;
    private long horario;

    /**
     * Construtor sem parâmetros.
     */
    public GeographicalCoordinate() {
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    /**
     * Construtor com parâmetros e horario da posição.
     *
     * @param latitude valor da latitude da coordenada geográfica.
     * @param longitude valor da longitude da coordenada geográfica.
     * @param horario horario em que a posição foi obtida.
     */
    public GeographicalCoordinate(double latitude, double longitude, long horario) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.horario = horario;
    }
    
    /**
     * Construtor com parâmetros sem horario da posição.
     *
     * @param latitude valor da latitude da coordenada geográfica.
     * @param longitude valor da longitude da coordenada geográfica.
     */
    public GeographicalCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Método que calcula a distância entre dois pontos baseados em suas
     * coordenadas geograficas.
     *
     * @param coordenadaGeografica1 latitude e longitude da coordenada
     * geográfica 1.
     * @param coordenadaGeografica2 latitude e longitude da coordenada
     * geográfica 2.
     * @return double distância em milhas nauticas.
     * @see #calcularDistancia(double, double, double, double)
     */
    public static double calcularDistancia(GeographicalCoordinate coordenadaGeografica1, GeographicalCoordinate coordenadaGeografica2) {
        if (coordenadaGeografica1 == null || coordenadaGeografica2 == null) {
            return Double.NaN;
        }

        return GeographicalCoordinate.distancia(coordenadaGeografica1.getLatitude(), coordenadaGeografica1.getLongitude(),
                coordenadaGeografica2.getLatitude(), coordenadaGeografica2.getLongitude());
    }

    /**
     * Método que calcula a distância entre dois pontos baseados em suas
     * coordenadas geograficas.
     *
     * @param latitude1 latitude da coordenada geográfica 1 (em décimo de graus)
     * @param longitude1 longitude da coordenada geográfica 1 (em décimo de
     * graus)
     * @param latitude2 latitude da coordenada geográfica 2 (em décimo de graus)
     * @param longitude2 longitude da coordenada geográfica 2 (em décimo de
     * graus)
     * @return double distância em milhas nauticas
     */
    public static double calcularDistancia(double latitude1, double longitude1, double latitude2, double longitude2) {
        return GeographicalCoordinate.distancia(latitude1, longitude1, latitude2, longitude2);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o instanceof GeographicalCoordinate) {
            GeographicalCoordinate cg = (GeographicalCoordinate) o;

            if (cg.getClass().equals(getClass())
                    && cg.hashCode() == hashCode()
                    && cg.getLatitude() == getLatitude()
                    && cg.getLongitude() == getLongitude()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém a latitude no formato decimal.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Define a latitude no formato decimal.
     */
    synchronized public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Obtém longitude no formato decimal.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Define a longitude no formato decimal.
     */
    synchronized public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    /**
     * Obtém o horário da aquisição da posição em milissegundos
     * @return 
     */
    public long getHorario() {
        return horario;
    }

    /**
     * Define o horário da aquisição da posição em milissegundos 
     * @param horario 
     */
    public void setHorario(long horario) {
        this.horario = horario;
    }
    
    

    @Override
    public String toString() {
        return latitudeDGtoGDM(latitude) + " " + longitudeDGtoGDM(longitude);
    }

    /**
     * Converte a latitude DG (DÉCIMO DE GRAUS) para o formato GDM
     * (XX°XX.XXX'[N|S]).
     *
     * @param latitude latitude em décimos de graus
     * @return latitude em (XX°XX.XXX'[N|S])
     */
    public static String latitudeDGtoGDM(double latitude) {
        double graus = (int) latitude;
        double minutos = Math.abs((latitude - graus) * 60.0);
        if (minutos > 59.999) {  // não permite 60 minutos
            minutos = 0.0;
            graus += Math.signum(graus);
        }
        String str = String.format(Locale.US, "%02.0f\u00b0%06.3f\u0027", Math.abs(graus), minutos);
        str += latitude >= 0.0 ? "N" : "S";
        return str;
    }

    /**
     * Converte a longitude DG (DÉCIMO DE GRAUS) para o formato GDM
     * (XXX°XX.XXX'[E|W]).
     *
     * @param longitude longitude em décimos de graus
     * @return longitude em (XXX°XX.XXX'[E|W])
     */
    public static String longitudeDGtoGDM(double longitude) {
        double graus = (int) longitude;
        double minutos = Math.abs((longitude - graus) * 60.0);
        if (minutos > 59.999) {  // não permite 60 minutos
            minutos = 0.0;
            graus += Math.signum(graus);
        }
        String str = String.format(Locale.US, "%03.0f\u00b0%06.3f\u0027", Math.abs(graus), minutos);
        str += longitude >= 0.0 ? "E" : "W";
        return str;
    }

    /**
     * Converte a latitude no formato String (XX°XX.XXX'[N|S]) ou
     * (XX°XX'XX''[E|W]) para o formato DG (DÉCIMO DE GRAUS).
     *
     * @param latitude latitude em décimos de graus
     * @return latitude em décimos de graus
     */
    public static double latitudeStringtoDG(String latitude) {
        if (latitude.matches("\\d\\d°\\d\\d.\\d\\d\\d'[Nn|Ss]")) {
            double graus = Double.parseDouble(latitude.substring(0, 2));
            double dMinutos = Double.parseDouble(latitude.substring(3, 9)) / 60;
            String sinal = latitude.substring(10, 11);
            if (sinal.equals("S")) {
                return (graus + dMinutos) * -1;
            } else {
                return (graus + dMinutos);
            }
        } else if (latitude.matches("\\d\\d°\\d\\d'\\d\\d\"[Nn|Ss]")) {
            double graus = Double.parseDouble(latitude.substring(0, 2));
            double dMinutos = Double.parseDouble(latitude.substring(3, 5)) / 60;
            double dSegundos = Double.parseDouble(latitude.substring(6, 8)) / 3600;
            String sinal = latitude.substring(9, 10);
            if (sinal.equals("S")) {
                return (graus + dMinutos + dSegundos) * -1;
            } else {
                return (graus + dMinutos + dSegundos);
            }
        }
        return 0;
    }

    /**
     * Converte a longitude no formato String (XXX°XX.XXX'[E|W]) ou
     * (XXX°XX'XX''[E|W]) para o formato DG (DÉCIMO DE GRAUS).
     *
     * @param longitude longitude em décimos de graus
     * @return longitude em décimos de graus
     */
    public static double longitudeStringtoDG(String longitude) {
        if (longitude.matches("\\d\\d\\d°\\d\\d.\\d\\d\\d'[Ee|Ww]")) {
            double graus = Double.parseDouble(longitude.substring(0, 3));
            double dMinutos = Double.parseDouble(longitude.substring(4, 10)) / 60;
            String sinal = longitude.substring(11, 12);
            if (sinal.equals("W")) {
                return (graus + dMinutos) * -1;
            } else {
                return (graus + dMinutos);
            }
        } else if (longitude.matches("\\d\\d\\d°\\d\\d'\\d\\d\"[Ee|Ww]")) {
            double graus = Double.parseDouble(longitude.substring(0, 3));
            double dMinutos = Double.parseDouble(longitude.substring(4, 6)) / 60;
            double dSegundos = Double.parseDouble(longitude.substring(7, 9)) / 3600;
            String sinal = longitude.substring(10, 11);
            if (sinal.equals("W")) {
                return (graus + dMinutos + dSegundos) * -1;
            } else {
                return (graus + dMinutos + dSegundos);
            }
        }
        return 0;
    }

    /**
     * Converte a latitude DG (DÉCIMO DE GRAUS) para o formato GMS
     * (XX°XX'XX''[E|W]).
     *
     * @param longitude latitude em décimos de graus
     * @return latitude em (XX°XX'XX''[E|W])
     */
    public static String longitudeDGtoGMS(double longitude) {
        int graus = (int) longitude;
        int minutos = (int) (60 * (longitude - graus));
        int segundos = (int) (((60 * (longitude - graus)) - minutos) * 60);

        String str = String.format(Locale.US, "%03d\u00b0%02d\u0027%02d\"", Math.abs(graus), Math.abs(minutos), Math.abs(segundos));
        str += longitude >= 0.0 ? "E" : "W";

        return str;
    }

    /**
     * Converte a latitude DG (DÉCIMO DE GRAUS) para o formato GMS
     * (XX°XX'XX''[E|W]).
     *
     * @param latitude latitude em décimos de graus
     * @return latitude em (XX°XX'XX''[E|W])
     */
    public static String latitudeDGtoGMS(double latitude) {

        int graus = (int) latitude;
        int minutos = (int) (60 * (latitude - graus));
        int segundos = (int) (((60 * (latitude - graus)) - minutos) * 60);

        String str = String.format(Locale.US, "%02d\u00b0%02d\u0027%02d\"", Math.abs(graus), Math.abs(minutos), Math.abs(segundos));
        str += latitude >= 0.0 ? "N" : "S";

        return str;
    }

    /**
     * Converte a latitude no formato GDM (GRAUS,DÉCIMO DE MINUTOS[N|S]) para o
     * formato DG (DÉCIMO DE GRAUS).
     *
     * @param latitude latitude em décimos de graus
     */
    public static double latitudeGDMtoDG(String latitude) {
        double graus = Double.parseDouble(latitude.substring(0, 2));
        double dMinutos = Double.parseDouble(latitude.substring(2, latitude.length() - 1)) / 60;
        String sinal = latitude.substring(latitude.length() - 1, latitude.length());
        if (sinal.equals("S")) {
            return (graus + dMinutos) * -1;
        } else {
            return (graus + dMinutos);
        }
    }

    public static double latitudeWgs84toDG(double latitude) {

        double graus = latitude / 100;
        double minutos = latitude - (100 * graus);
        double dMinutos = (minutos / 60);
        return graus + dMinutos;
    }

    /**
     * Converte a longitude no formato GDM (GRAUS,DÉCIMO DE MINUTOS[W|E]) para o
     * formato DG (DÉCIMO DE GRAUS).
     *
     * @param longitude longitude em décimos de graus
     */
    public static double longitudeGDMtoDG(String longitude) {
        double graus = Double.parseDouble(longitude.substring(0, 3));
        double dMinutos = Double.parseDouble(longitude.substring(3, longitude.length() - 1)) / 60;
        String sinal = longitude.substring(longitude.length() - 1, longitude.length());
        if (sinal.equals("W")) {
            return (graus + dMinutos) * -1;
        } else {
            return (graus + dMinutos);
        }
    }

    /**
     * Converte a latitude no formato formato DG (DÉCIMO DE GRAUS) para o
     * formato GDM (GRAUS,DÉCIMO DE MINUTOS[N|S]).
     *
     * @param latitude latitude em graus e décimo de minutos.
     */
    public static String latitudeDGtoDGM(double latitude) {
        double graus = (int) latitude;
        double minutos = Math.abs((latitude - graus) * 60.0);
        String str = String.format(Locale.US, "%2.0f%06.3f", Math.abs(graus), minutos);
        str += latitude > 0.0 ? "N" : "S";
        return str;
    }

    /**
     * Converte a longitude no formato formato DG (DÉCIMO DE GRAUS) para o
     * formato GDM (GRAUS,DÉCIMO DE MINUTOS[E|W]).
     *
     * @param longitude longitude em graus e décimo de minutos.
     */
    public static String longitudeDGtoDGM(double longitude) {
        double graus = (int) longitude;
        double minutos = Math.abs((longitude - graus) * 60.0);
        String str = String.format(Locale.US, "%3.0f%06.3f", Math.abs(graus), minutos);
        str = str.replaceAll(" ", "0");
        str += longitude > 0.0 ? "E" : "W";
        return str;
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
    /* Vincenty Inverse Solution of Geodesics on the Ellipsoid (c) Chris Veness 2002-2012             */
    /*                                                                                                */
    /* from: Vincenty inverse formula - T Vincenty, "Direct and Inverse Solutions of Geodesics on the */
    /*       Ellipsoid with application of nested equations", Survey Review, vol XXII no 176, 1975    */
    /*       http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf                                             */
    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
    /**
     * Calculates geodetic distance between two points specified by
     * latitude/longitude using Vincenty inverse formula for ellipsoids
     *
     * @param {Number} lat1, lon1: first point in decimal degrees
     * @param {Number} lat2, lon2: second point in decimal degrees
     * @returns (Number} distance in metres between points
     */
    private static double distancia(double lat1, double lon1, double lat2, double lon2) {
        double a = 6378137;
        double b = 6356752.314245;
        double f = 1 / 298.257223563;  // WGS-84 ellipsoid params
        double L = Math.toRadians(lon2 - lon1);
        double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
        double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);
        double lambda = L;
        double lambdaP;
        int iterLimit = 100;
        double sinLambda;
        double cosLambda;
        double sinSigma;
        double cosSqAlpha;
        double cosSigma;
        double sigma;
        double cos2SigmaM;
        do {
            sinLambda = Math.sin(lambda);
            cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
                    * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if (sinSigma == 0) {
                return 0;  // co-incident points
            }
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            if (Double.isNaN(cos2SigmaM)) {
                cos2SigmaM = 0;  // equatorial line: cosSqAlpha=0 (§6)
            }
            double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

        if (iterLimit == 0) {
            return Double.NaN;  // formula failed to converge
        }
        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
                - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
        double s = b * A * (sigma - deltaSigma);

//        // note: to return initial/final bearings in addition to distance, use something like:
//        double fwdAz = Math.toDegrees(Math.atan2(cosU2*sinLambda,  cosU1*sinU2-sinU1*cosU2*cosLambda));
//        double revAz = Math.toDegrees(Math.atan2(cosU1*sinLambda, -sinU1*cosU2+cosU1*sinU2*cosLambda));
        return s / 1852.0;  // nautical miles
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
    /* Vincenty Direct Solution of Geodesics on the Ellipsoid (c) Chris Veness 2005-2012              */
    /*                                                                                                */
    /* from: Vincenty direct formula - T Vincenty, "Direct and Inverse Solutions of Geodesics on the  */
    /*       Ellipsoid with application of nested equations", Survey Review, vol XXII no 176, 1975    */
    /*       http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf                                             */
    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
    /**
     * Calculates destination point given start point lat/long, bearing &
     * distance, using Vincenty inverse formula for ellipsoids
     *
     * @param {Number} lat1, lon1: first point in decimal degrees
     * @param {Number} brng: initial bearing in decimal degrees
     * @param {Number} dist: distance along bearing in nautical miles
     * @returns (LatLon} destination point
     */
//    private static Point2DGeneric.Double destino(double lat, double lon, double marcacao, double distancia) {
//        double a = 6378137;
//        double b = 6356752.3142;
//        double f = 1 / 298.257223563;  // WGS-84 ellipsiod
//        double s = distancia * 1852.0;  // meters
//        double alpha1 = Math.toRadians(marcacao);
//        double sinAlpha1 = Math.sin(alpha1);
//        double cosAlpha1 = Math.cos(alpha1);
//
//        double tanU1 = (1 - f) * Math.tan(Math.toRadians(lat));
//        double cosU1 = 1 / Math.sqrt((1 + tanU1 * tanU1));
//        double sinU1 = tanU1 * cosU1;
//        double sigma1 = Math.atan2(tanU1, cosAlpha1);
//        double sinAlpha = cosU1 * sinAlpha1;
//        double cosSqAlpha = 1 - sinAlpha * sinAlpha;
//        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
//        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
//        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
//
//        double sigma = s / (b * A);
//        double sinSigma = Math.sin(sigma);
//        double cosSigma = Math.cos(sigma);
//        double cos2SigmaM = Math.cos(2 * sigma1 + sigma);
//        double sigmaP = 2 * Math.PI;
//        while (Math.abs(sigma - sigmaP) > 1e-12) {
//            cos2SigmaM = Math.cos(2 * sigma1 + sigma);
//            sinSigma = Math.sin(sigma);
//            cosSigma = Math.cos(sigma);
//            double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
//                    - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
//            sigmaP = sigma;
//            sigma = s / (b * A) + deltaSigma;
//        }
//
//        double tmp = sinU1 * sinSigma - cosU1 * cosSigma * cosAlpha1;
//        double lat2 = Math.atan2(sinU1 * cosSigma + cosU1 * sinSigma * cosAlpha1, (1 - f) * Math.sqrt(sinAlpha * sinAlpha + tmp * tmp));
//        double lambda = Math.atan2(sinSigma * sinAlpha1, cosU1 * cosSigma - sinU1 * sinSigma * cosAlpha1);
//        double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
//        double L = lambda - (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
//        double lon2 = (Math.toRadians(lon) + L + 3 * Math.PI) % (2 * Math.PI) - Math.PI;  // normalise to -180...+180
//
//        //double revAz = Math.atan2(sinAlpha, -tmp);  // final bearing, if required
//        return new Point2DGeneric.Double(Math.toDegrees(lon2), Math.toDegrees(lat2));
//    }
}
