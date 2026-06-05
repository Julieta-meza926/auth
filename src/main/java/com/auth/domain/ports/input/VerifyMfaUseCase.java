package com.auth.domain.ports.input;

import com.auth.application.dto.MfaVerifyRequest;
import com.auth.application.dto.MfaVerifyResponse;

public interface VerifyMfaUseCase {

    MfaVerifyResponse verify(
            MfaVerifyRequest request
    );

}
