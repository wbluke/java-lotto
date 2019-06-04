package lotto.domain.lottoticket;

import lotto.domain.Rank;
import lotto.domain.lottonumber.LottoNumber;

import java.util.*;

public class LottoTicket {
    static final int SIZE_OF_LOTTO = 6;
    private static final String LOTTO_SIZE_ERROR_MESSAGE = "로또는 숫자 개수는 " + SIZE_OF_LOTTO + "개입니다.";
    private static final String DUPLICATE_ERROR_MESSAGE = "중복된 숫자가 있습니다.";
    private final List<LottoNumber> lottoNumbers;

    public LottoTicket(List<LottoNumber> lottoNumbers) {
        if (isIncorrectSize(lottoNumbers)) {
            throw new InvalidLottoTicketException(LOTTO_SIZE_ERROR_MESSAGE);
        }
        if (isDuplicated(lottoNumbers)) {
            throw new InvalidLottoTicketException(DUPLICATE_ERROR_MESSAGE);
        }

        this.lottoNumbers = lottoNumbers;
        sortLottoNumbersInAscendingOrder();
    }

    private boolean isIncorrectSize(List<LottoNumber> lottoNumbers) {
        return lottoNumbers.size() != SIZE_OF_LOTTO;
    }

    private boolean isDuplicated(List<LottoNumber> lottoNumbers) {
        Set<LottoNumber> filteredLottoNumbers = new HashSet<>(lottoNumbers);
        return lottoNumbers.size() != filteredLottoNumbers.size();
    }

    private void sortLottoNumbersInAscendingOrder() {
        Collections.sort(lottoNumbers);
    }

    public boolean match(LottoNumber lottoNumber) {
        return lottoNumbers.contains(lottoNumber);
    }

    public Rank match(LottoTicket winningLotto, LottoNumber bonusBall) {
        int numOfMatching = (int) lottoNumbers.stream().filter(winningLotto::match).count();
        return Rank.valueOf(numOfMatching, lottoNumbers.contains(bonusBall));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LottoTicket that = (LottoTicket) o;
        return Objects.equals(lottoNumbers, that.lottoNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lottoNumbers);
    }

    @Override
    public String toString() {
        return lottoNumbers.toString();
    }
}
