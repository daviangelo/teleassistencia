package positioning;

/**
 * Delimitador de janela.
 */
public class BoundingBox {

    private GeographicalCoordinate min, max;

    public BoundingBox() {
    }

    public BoundingBox(GeographicalCoordinate min, GeographicalCoordinate max) {
        this.min = min;
        this.max = max;
    }

    public BoundingBox(double minLon, double minLat, double maxLon, double maxLat) {
        min = new GeographicalCoordinate(minLat, minLon);
        max = new GeographicalCoordinate(maxLat, maxLon);
    }
    
    public double getLargura() {
        return GeographicalCoordinate.calcularDistancia(min.getLatitude(), min.getLongitude(), min.getLatitude(), max.getLongitude());
    }
    
    public double getAltura() {
        return GeographicalCoordinate.calcularDistancia(min.getLatitude(), min.getLongitude(), max.getLatitude(), min.getLongitude());
    }

    public boolean contem(BoundingBox janela) {
        return janela.getMin().getLongitude() > min.getLongitude()
                & janela.getMax().getLongitude() < max.getLongitude()
                & janela.getMin().getLatitude() > min.getLatitude()
                & janela.getMax().getLatitude() < max.getLatitude();
    }

    public boolean intercepta(BoundingBox janela) {
        return !(janela.getMin().getLongitude() > max.getLongitude()
                || janela.getMax().getLongitude() < min.getLongitude()
                || janela.getMin().getLatitude() > max.getLatitude()
                || janela.getMax().getLatitude() < min.getLatitude());
    }
    
    
    /**
     * Metodo usado para verificar se um ponto intercepta uma janela geografica.
     * Se o ponto intercepta a janela geografica: 
     * - Sua latitude e menor que a latitude da maior coordenada da janela 
     * geografica e maior que a latitude da menor coordenada da janela 
     * geografica.
     * - Sua longitude e menor que a longitude da maior coordenada da janela 
     * geografica e maior que a longitude da menor coordenada da janela 
     * geografica.
     * 
     * @param ponto a GeographicalCoordinate do ponto que se deseja saber se 
     *              intercepta a janela geografica.
     * 
     * @return um booleano que diz se o ponto intercepta ou nao a janela 
     *         geografica.
     */
    public boolean intercepta(GeographicalCoordinate ponto) {
        return !(ponto.getLongitude() > max.getLongitude()
                || ponto.getLongitude() < min.getLongitude()
                || ponto.getLatitude() > max.getLatitude()
                || ponto.getLatitude() < min.getLatitude());
    }
    

    @Override
    public String toString() {
        return "(" + this.getMin().toString() + ") - (" + this.getMax().toString() + ")";
    }

    /**
     * @return the min
     */
    public GeographicalCoordinate getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(GeographicalCoordinate min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public GeographicalCoordinate getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(GeographicalCoordinate max) {
        this.max = max;
    }
}
