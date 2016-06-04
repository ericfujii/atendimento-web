package br.com.ericfujii.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataUtil {
	public static long calcularDiferencaSegundos(Calendar dataHorarioInicio, Calendar dataHorarioFim) {
		if ((dataHorarioInicio == null || dataHorarioFim == null)
				|| (dataHorarioFim.before(dataHorarioInicio))) {
			throw new IllegalArgumentException("Dados de entrada de data invalidos!");
		}
		return ((dataHorarioFim.getTimeInMillis() - dataHorarioInicio.getTimeInMillis()) 
				/ 1000);
	}
	
	public static String formatarData(Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(calendar.getTime());
	}
}
