import java.util.Arrays;

public class TarifaTelefonica {

	private int tarifaPlana, permanencia, tarifaMegas;
	private int[] estimacion;
	private int[] pago;// Pago mínimo a realizar durante los meses de i...n

	public TarifaTelefonica(int tp, int p, int tm, int[] est) {
		tarifaPlana = tp;
		permanencia = p;
		tarifaMegas = tm;
		estimacion = est;
		pago = null;
	}

	public int resolverBottomUp() {
		int aux = estimacion.length;
		pago=new int[aux+1];
		Arrays.fill(pago, Integer.MAX_VALUE);
		pago[aux] = 0;

		for(int i = aux-1; i>=0; i--){
			pago[i] = estimacion[i]*tarifaMegas+pago[i+1];
			if(i+permanencia<=aux){
				int costoTarifaPlana =tarifaPlana*permanencia+pago[i+permanencia];
				if(costoTarifaPlana<pago[i]){
					pago[i]=costoTarifaPlana;
				}
			}
		}
		return pago[0];
	}

	public int[] reconstruirSol() {
		if (pago == null) {
			throw new RuntimeException("Se debe resolver el problema primero");
		}

		int tam = estimacion.length;
		int[] plan = new int[tam];

		for(int i = 0; i<tam;){
			if(pago[i]==estimacion[i]*tarifaMegas+pago[i+1]){
				plan[i]=0;
				i++;
			}else{
				for(int j = 0; j<permanencia; j++){
					if(i+j<tam){
						plan[i+j]=1;
					}
				}
				i+=permanencia;
			}
		}
		return plan;
	}

	public void imprimeVectorSolucion() {
		System.out.println(Arrays.toString(pago));
	}

}