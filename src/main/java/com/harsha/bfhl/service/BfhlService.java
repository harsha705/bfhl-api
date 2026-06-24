package com.harsha.bfhl.service;

import com.harsha.bfhl.dto.BfhlRequest;
import com.harsha.bfhl.dto.BfhlResponse;

public interface BfhlService {

    BfhlResponse processData(BfhlRequest request);
}
