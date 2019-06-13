package lotto.application.lottoticket;

import lotto.application.lottoresult.LottoResultService;
import lotto.domain.lottonumber.LottoNumber;
import lotto.domain.lottonumber.LottoNumberPool;
import lotto.domain.lottoticket.InvalidLottoTicketException;
import lotto.domain.lottoticket.LottoTicket;
import lotto.domain.lottoticket.LottoTickets;
import lotto.domain.lottoticket.dto.LottoTicketDto;
import lotto.domain.lottoticket.dto.LottoTicketsDto;

import java.util.List;
import java.util.stream.Collectors;

public class LottoTicketService {
    private static final String ERROR_OUT_OF_BOUND_NUM_OF_MANUAL_LOTTO = "기존에 입력한 로또 수보다 많습니다.";

    private LottoTicketService() {
    }

    public static long getNumOfManualLotto(String num, String numOfLotto) {
        long numOfManualLotto = Long.parseLong(num);
        if (numOfManualLotto > Long.parseLong(numOfLotto)) {
            throw new InvalidLottoTicketException(ERROR_OUT_OF_BOUND_NUM_OF_MANUAL_LOTTO);
        }
        return numOfManualLotto;
    }

    public static LottoTicket makeLottoTicket(List<String> numbers) {
        List<LottoNumber> lottoNumbers = numbers.stream()
                .map(Integer::parseInt)
                .map(LottoNumberPool::valueOf)
                .collect(Collectors.toList());
        return new LottoTicket(lottoNumbers);
    }

    public static LottoTicketDto getLottoTicketDto(LottoTicket lottoTicket) {
        return LottoTicketAssembler.getLottoTicketDto(lottoTicket);
    }

    public static LottoTickets getAutomaticLottoTickets(String num) {
        long numOfAutomaticLotto = Long.parseLong(num);
        return LottoTicketAssembler.getAutomaticLottoTickets(numOfAutomaticLotto);
    }

    public static LottoTicketsDto getLottoTicketsDto(LottoTickets automaticTickets) {
        return LottoTicketAssembler.getLottoTicketsDto(automaticTickets);
    }

    public static LottoNumber makeBonusBall(String num) {
        return LottoNumberPool.valueOf(Integer.parseInt(num));
    }

    public static void savePurchasedLottoTicket(LottoTicketDto lottoTicketDto) {
        int currentRound = LottoResultService.fetchCurrentRound();
        saveLottoTicket(currentRound, lottoTicketDto);
    }

    private static void saveLottoTicket(int currentRound, LottoTicketDto lottoTicketDto) {
        LottoTicketDAO lottoTicketDAO = LottoTicketDAO.getInstance();
        lottoTicketDAO.savePurchasedLotto(currentRound, lottoTicketDto);
    }

    public static void savePurchasedLottoTickets(LottoTicketsDto lottoTicketsDto) {
        int currentRound = LottoResultService.fetchCurrentRound();
        List<LottoTicketDto> dtos = lottoTicketsDto.getLottoTicketDtos();
        for (LottoTicketDto lottoTicketDto : dtos) {
            saveLottoTicket(currentRound, lottoTicketDto);
        }
    }
}
